CREATE TABLE `events` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,      -- 행사 ID (자동 증가)
  `title` VARCHAR(255) NOT NULL,                      -- 행사 제목
  `address1` VARCHAR(255),                            -- 주소 1
  `address2` VARCHAR(255),                            -- 주소 2
  `booktour` VARCHAR(255),                            -- 예약 정보 (필요한 경우)
  `category1` VARCHAR(10),                            -- 카테고리 1 (A02 등)
  `category2` VARCHAR(10),                            -- 카테고리 2 (A0208 등)
  `category3` VARCHAR(20),                            -- 카테고리 3 (A02081300 등)
  `content_id` INT,                                   -- 콘텐츠 ID
  `content_type_id` INT,                              -- 콘텐츠 타입 ID
  `created_time` DATETIME,                            -- 생성 시간
  `event_start_date` DATE,                            -- 행사 시작 날짜
  `event_end_date` DATE,                              -- 행사 종료 날짜
  `first_image` VARCHAR(255),                         -- 첫 번째 이미지 URL
  `first_image2` VARCHAR(255),                        -- 두 번째 이미지 URL
  `copyright_div_code` VARCHAR(10),                   -- 저작권 구분 코드 (Type3 등)
  `mapx` DECIMAL(65, 30),                              -- 장소 X 좌표 (위도)
  `mapy` DECIMAL(65, 30),                              -- 장소 Y 좌표 (경도)
  `m_level` INT,                                      -- M Level
  `modified_time` DATETIME,                           -- 수정 시간
  `area_code` INT,                                    -- 지역 코드
  `sigungu_code` INT,                                 -- 시군구 코드
  `tel` VARCHAR(50),                                  -- 전화번호
  FOREIGN KEY (`area_code`) REFERENCES `sidos` (`id`),  -- 지역 코드 참조 (외래 키, regions 테이블 필요)
  FOREIGN KEY (`sigungu_code`) REFERENCES `guguns` (`id`) -- 시군구 코드 참조 (외래 키, guguns 테이블 필요)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
