import urllib.request
import urllib.parse
import json
import os
import pandas as pd
import time
import json
import pymysql
from datetime import datetime
# 데이터베이스 연결 설정
def get_db_connection():
    return pymysql.connect(
        host='localhost',
        user='ssafy',
        password='ssafy',
        database='trip',
        charset='utf8mb4',
        cursorclass=pymysql.cursors.DictCursor
    )
# secrets.json 파일 열기
with open('../src/main/resources/secrets.json', 'r') as f:
    secrets = json.load(f)

CLIENT_ID = secrets.get('CLIENT_ID')
CLIENT_SECRET = secrets.get('CLIENT_SECRET')
FILE_PATH = "data.json"

def get_regions():
    """sidos와 guguns 정보를 가져옵니다."""
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            sql = """
                SELECT g.no, s.sido_name, g.gugun_name
                FROM sidos s
                JOIN guguns g ON s.sido_code = g.sido_code;
            """
            cursor.execute(sql)
            return cursor.fetchall()
    finally:
        connection.close()
def insert_blog_data(blogs, gugun_no):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            sql = """
                INSERT INTO naver_blogs (title, gugun_no, link, description, bloggername, bloggerlink, created_date)
                VALUES (%s, %s, %s, %s, %s, %s, %s);
            """
            for blog in blogs:
                # 먼저 날짜 문자열을 datetime 객체로 변환
                formatted_date = datetime.strptime(blog["created_date"], '%Y%m%d').strftime('%Y-%m-%d')
                cursor.execute(sql, (
                    blog["title"],
                    gugun_no,
                    blog["link"],
                    blog["description"],
                    blog["bloggername"],
                    blog["bloggerlink"],
                    formatted_date
                ))
        connection.commit()
    finally:
        connection.close()

def insert_place_data(places, gugun_no):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            sql = """
                INSERT INTO naver_places (title, gugun_no, category, address, road_address, mapx, mapy, link)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s);
            """
            for place in places:
                cursor.execute(sql, (
                    place["title"],
                    gugun_no,
                    place["category"],
                    place["address"],
                    place["road_address"],
                    place["mapx"],
                    place["mapy"],
                    place["link"]
                ))
        connection.commit()
    finally:
        connection.close()

def insert_news_data(news, gugun_no):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            sql = """
                INSERT INTO naver_news (title, gugun_no, link, description, created_date)
                VALUES (%s, %s, %s, %s, %s);
            """
            for item in news:
                # 먼저 날짜 문자열을 datetime 객체로 변환
                created_date = datetime.strptime(item["created_date"], '%a, %d %b %Y %H:%M:%S %z')

                # MySQL에서 사용할 수 있는 형식으로 변환 (YYYY-MM-DD HH:MM:SS)
                formatted_date = created_date.strftime('%Y-%m-%d %H:%M:%S')
                cursor.execute(sql, (
                    item["title"],
                    gugun_no,
                    item["link"],
                    item["description"],
                    formatted_date
                ))
            connection.commit()
    finally:
        connection.close()

def request_api(url, params):
    """네이버 API에 요청하고 JSON 데이터를 반환."""
    query_string = urllib.parse.urlencode(params)
    full_url = f"{url}?{query_string}"
    request = urllib.request.Request(full_url)
    request.add_header("X-Naver-Client-Id", CLIENT_ID)
    request.add_header("X-Naver-Client-Secret", CLIENT_SECRET)
    try:
        response = urllib.request.urlopen(request)
        if response.getcode() == 200:
            response_body = response.read()
            return json.loads(response_body.decode('utf-8'))
        else:
            print(f"Error Code: {response.getcode()}")
    except Exception as e:
        print(f"An error occurred: {e}")
    return None

def search_blog(query, display=100, start=1, sort="date"):
    """네이버 블로그 검색 API"""
    url = "https://openapi.naver.com/v1/search/blog.json"
    params = {
        "query": query,
        "display": display,
        "start": start,
        "sort": sort
    }
    data = request_api(url, params)
    if data:
        return [{
            "title": item["title"],
            "region": query.split()[0],  # 검색어 첫 단어를 지역
            "link": item["link"],
            "description": item["description"],
            "bloggername": item["bloggername"],
            "bloggerlink": item["bloggerlink"],
            "created_date": item["postdate"]
        } for item in data.get("items", [])]
    return []

def search_news(query, display=100, start=1, sort="date"):
    """네이버 뉴스 검색 API"""
    url = "https://openapi.naver.com/v1/search/news.json"
    params = {
        "query": query,
        "display": display,
        "start": start,
        "sort": sort
    }
    data = request_api(url, params)
    if data:
        return [{
            "title": item["title"],
            "region": query.split()[0],  # 검색어 첫 단어를 지역
            "link": item["link"],
            "description": item["description"],
            "created_date": item["pubDate"]
        } for item in data.get("items", [])]
    return []

def search_place(query, display=5, start=1, sort="comment"):
    """네이버 지역 검색 API"""
    url = "https://openapi.naver.com/v1/search/local.json"
    params = {
        "query": query,
        "display": display,
        "start": start,
        "sort": sort
    }
    data = request_api(url, params)
    if data:
        return [{
            "title": item["title"],
            "region": query.split()[0],  # 검색어 첫 단어를 지역
            "category": item["category"],
            "address": item["address"],
            "road_address": item["roadAddress"],
            "mapx": item["mapx"],
            "mapy": item["mapy"],
            "link": item["link"]
        } for item in data.get("items", [])]
    return []


# Example usage
if __name__ == "__main__":
    #시작 시간
    start_time = time.time()
    
    region = "서울"
    
    regions = get_regions()  # 지역 정보 가져오기
    
    # # 새로운 데이터 검색
    for region in regions:
        gugun_no = region["no"]
        sido = region["sido_name"]
        gugun = region["gugun_name"]
        full_region = f"{sido} {gugun}"
        print(f"{full_region} 탐색 진행 중")
        # 1. 식당
        blogs = search_blog(f"{full_region} 혼밥 식당", start=1)
        places = search_place(f"{full_region} 혼밥 식당", start=1)
        # 2. 숙소
        blogs = search_blog(f"{full_region} 1인 숙소", start=1)
        places = search_place(f"{full_region} 1인 숙소", start=1)
        
        # 3. 여행지
        blogs = search_blog(f"{full_region} 여행지", start=1)
        places = search_place(f"{full_region} 여행지", start=1)
        
        # 4. 행사 정보 - tour api
        news = search_news(f"{full_region} 축제", start=1)
        news = search_news(f"{full_region} 행사", start=1)
        print(f"{full_region} 탐색 완료")
        
        # 기존 데이터에 새 데이터 추가
        print(f"{full_region} 데이터 추가 중")
        insert_blog_data(blogs,gugun_no)
        insert_place_data(places,gugun_no)
        insert_news_data(news,gugun_no)
        print(f"{region} 데이터 추가 완료")
        
        # 혹시 모를 쉬는 시간
        time.sleep(2)
        print("2초간 쉬는즁")
    
    #끝나는 시간
    end_time = time.time()
    
    # 실행 시간 계산
    execution_time = end_time - start_time

    # 실행 시간 출력
    print(f"실행 시간: {execution_time:.2f} 초")
