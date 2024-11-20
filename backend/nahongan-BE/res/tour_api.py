import urllib.request
import urllib.parse
import json
import xml.etree.ElementTree as ET
import time
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

TOURAPI_KEY = secrets.get('TOURAPI_KEY')

# def get_regions():
#     """sidos와 guguns 정보를 가져옵니다."""
#     connection = get_db_connection()
#     try:
#         with connection.cursor() as cursor:
#             sql = """
#                 SELECT g.sido_code, g.gugun_code, s.sido_name, g.gugun_name
#                 FROM sidos s
#                 JOIN guguns g ON s.sido_code = g.sido_code;
#             """
#             cursor.execute(sql)
#             return cursor.fetchall()
#     finally:
#         connection.close()

def insert_event_data(items):
    connection = get_db_connection()
    try:
        with connection.cursor() as cursor:
            sql = """
                INSERT INTO events (
                    title, created_time, event_start_date, event_end_date, 
                    address1, address2, booktour, category1, category2, category3, 
                    content_id, content_type_id, first_image, first_image2, copyright_div_code, 
                    mapx, mapy, m_level, modified_time, area_code, sigungu_code, tel
                ) 
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s);
            """
            for item in items:
                # 날짜 문자열을 datetime 객체로 변환
                cursor.execute(sql, (
                    item["title"],            # 행사 제목             # 구군 번호
                    item["createdtime"],            # 생성 시간
                    item["eventstartdate"],        # 행사 시작 날짜
                    item["eventenddate"],          # 행사 종료 날짜
                    item["address1"],         # 주소1
                    item["address2"],                      # 주소2 (빈 값으로 설정)
                    "",                      # 예약 정보 (빈 값으로 설정)
                    item["cat1"],       # 카테고리 1
                    item["cat2"],       # 카테고리 2
                    item["cat3"],       # 카테고리 3
                    item["contentid"],      # 콘텐츠 ID
                    item["contenttypeid"], # 콘텐츠 타입 ID
                    item["firstimage"],     # 첫 번째 이미지 URL
                    item["firstimage2"],    # 두 번째 이미지 URL
                    item["cpyrhtDivCd"], # 저작권 구분 코드
                    item["mapx"],            # 지도 X 좌표
                    item["mapy"],            # 지도 Y 좌표
                    item["mlevel"],         # M Level
                    item["modifiedtime"],           # 수정 시간
                    item["areacode"],       # 지역 코드
                    item["sigungucode"],    # 시군구 코드
                    item["tel"],             # 전화번호
                ))
        connection.commit()
    finally:
        connection.close()

def request_api(url, params):
    """네이버 API에 요청하고 JSON 데이터를 반환."""
    query_string = urllib.parse.urlencode(params)
    full_url = f"{url}?{query_string}"
    request = urllib.request.Request(full_url)
    print(request)
    try:
        response = urllib.request.urlopen(request)
        if response.getcode() == 200:
            response_body = response.read()
            print(response_body)
            root = ET.fromstring(response_body)  # XML 파싱
            items = root.findall(".//item")
            return items
        else:
            print(f"Error Code: {response.getcode()}")
    except Exception as e:
        print(f"An error occurred: {e}")
    return None

def search_events():
    """TOUR API"""
    url = "http://apis.data.go.kr/B551011/KorService1/searchFestival1"
    params = {
        "numOfRows" : 10,
        "serviceKey" : TOURAPI_KEY,
        "MobileOS" : "ETC",
        "MobileApp" : "nahonjaganda",
        "eventStartDate" : "20241101"
    }
    data = request_api(url, params)
    print(data)
    #https://apis.data.go.kr/B551011/KorService1/searchFestival1?numOfRows=10&MobileOS=ETC&MobileApp=nahonjaganda&eventStartDate=20241001&serviceKey=
    result = []
    if data:
        for item in data:
                event_data = {
                    "title": item.find("title").text if item.find("title") is not None else "",
                    "address1": item.find("addr1").text if item.find("addr1") is not None else "",
                    "address2": item.find("addr2").text if item.find("addr2") is not None else "",
                    "booktour": item.find("booktour").text if item.find("booktour") is not None else "",
                    "cat1": item.find("cat1").text if item.find("cat1") is not None else "",
                    "cat2": item.find("cat2").text if item.find("cat2") is not None else "",
                    "cat3": item.find("cat3").text if item.find("cat3") is not None else "",
                    "contentid": item.find("contentid").text if item.find("contentid") is not None else "",
                    "contenttypeid": item.find("contenttypeid").text if item.find("contenttypeid") is not None else "",
                    "createdtime": datetime.strptime(item.find("createdtime").text, "%Y%m%d%H%M%S").strftime("%Y-%m-%d %H:%M:%S") if item.find("createdtime") is not None else "",
                    "eventstartdate": datetime.strptime(item.find("eventstartdate").text, "%Y%m%d").strftime("%Y-%m-%d") if item.find("eventstartdate") is not None else "",
                    "eventenddate": datetime.strptime(item.find("eventenddate").text, "%Y%m%d").strftime("%Y-%m-%d") if item.find("eventenddate") is not None else "",
                    "firstimage": item.find("firstimage").text if item.find("firstimage") is not None else "",
                    "firstimage2": item.find("firstimage2").text if item.find("firstimage2") is not None else "",
                    "cpyrhtDivCd": item.find("cpyrhtDivCd").text if item.find("cpyrhtDivCd") is not None else "",
                    "mapx": item.find("mapx").text if item.find("mapx") is not None else "",
                    "mapy": item.find("mapy").text if item.find("mapy") is not None else "",
                    "mlevel": item.find("mlevel").text if item.find("mlevel") is not None else "",
                    "modifiedtime": datetime.strptime(item.find("modifiedtime").text, "%Y%m%d%H%M%S").strftime("%Y-%m-%d %H:%M:%S") if item.find("modifiedtime") is not None else "",
                    "areacode": item.find("areacode").text if item.find("areacode") is not None else "",
                    "sigungucode": item.find("sigungucode").text if item.find("sigungucode") is not None else "",
                    "tel": item.find("tel").text if item.find("tel") is not None else ""
                }
                result.append(event_data)
    return result

# Example usage
if __name__ == "__main__":
    start_time = time.time()
    
    # regions = get_regions()  # 지역 정보 가져오기
    
    # for region in regions:
    #     gugun_no = region["gugun_code"]
    #     sido = region["sido_name"]
    #     gugun = region["gugun_name"]
    #     full_region = f"{sido} {gugun}"
    #     print(f"{full_region} 탐색 진행 중")
        
    #     time.sleep(2)  # 혹시 모를 쉬는 시간
    #     print("2초간 쉬는중")
        
    events_data = search_events()  # 새로운 데이터 검색
    print(events_data)
    if events_data:
        insert_event_data(events_data)  # 해당 구군에 맞는 데이터 삽입
        
    end_time = time.time()
    execution_time = end_time - start_time
    print(f"실행 시간: {execution_time:.2f} 초")
