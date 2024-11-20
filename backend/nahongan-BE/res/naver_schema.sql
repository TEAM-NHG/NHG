use trip;
CREATE TABLE naver_places (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- 장소 ID
    title VARCHAR(255) NOT NULL,        -- 장소 이름
    gugun_no INT,                      -- 구군 테이블의 no 컬럼 참조
    category VARCHAR(255),              -- 장소 카테고리
    address VARCHAR(255),               -- 주소
    road_address VARCHAR(255),          -- 도로명 주소
    mapx DECIMAL(65, 30),                -- 장소의 X 좌표 (위도)
    mapy DECIMAL(65, 30),                -- 장소의 Y 좌표 (경도)
    link VARCHAR(255),                  -- 장소 링크
    FOREIGN KEY (gugun_no) REFERENCES guguns(no) -- 외래키 (구군의 no 참조)
);

CREATE TABLE naver_news (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- 뉴스 기사 ID
    title VARCHAR(255) NOT NULL,        -- 뉴스 기사 제목
    gugun_no INT,                      -- 지역 (regions 테이블의 외래키)
    link VARCHAR(255) NOT NULL,         -- 뉴스 링크
    description TEXT,                   -- 뉴스 기사 설명
    created_date DATETIME,              -- 뉴스 기사 작성일
    FOREIGN KEY (gugun_no) REFERENCES guguns(no)  -- 외래키 (지역)
);

CREATE TABLE naver_blogs (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- 블로그 포스트 ID
    title VARCHAR(255) NOT NULL,        -- 블로그 포스트 제목
    gugun_no INT,                      -- 지역 (regions 테이블의 외래키)
    link VARCHAR(255) NOT NULL,         -- 블로그 링크
    description TEXT,                   -- 블로그 포스트 설명
    bloggername VARCHAR(255),           -- 블로거 이름
    bloggerlink VARCHAR(255),           -- 블로거 링크
    created_date DATETIME,              -- 포스트 작성일
    FOREIGN KEY (gugun_no) REFERENCES guguns(no)  -- 외래키 (지역)
);
