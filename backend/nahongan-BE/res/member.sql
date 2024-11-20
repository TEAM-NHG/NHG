drop database if exists trip;
create database trip;
use trip;
drop table if exists `members`;

CREATE TABLE `members` (
  `id` varchar(16) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nickname` varchar(32) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DELETE FROM `members`;

insert into `members` (id, password, nickname, email) values
('admin','1234','관리자','google.com'),
('hissam','9876','안효인','ssafy.com'),
('ssafy','1234','김싸피','ssafy.com'),
('ssafy.hong','1234','홍싸피','ssafy.com'),
('ssafy.kim','1234','김싸피','ssafy.com'),
('vlfxhd69','1234','알고왕', 'vlfxhd69@ssafy.com'),
('dr8766', 'q1w2e3r4', '귀족민재', 'dr8766@gmail.com'),
('max8276', 'u23i3i1', '최강현준', 'max8276@naver.com'),
('tmddnjs3283', 'eqwe23515', '승워니', 'tmddnjs3283@naver.com'),
('jeri_mm', 'klrjlj3211', '제리', 'jeri_mm@ssafy.com'),
('rladudwns164', 'dkfji223', '광김', 'rladudwns164@daum.net'),
('hjk2060', 'auiof8333', '음', 'hjk2060@gmail.com'),
('sung515', '231jkfj', '성오', 'sung515@ssafy.com'),
('huh0529', '3999a9a21', '인주인줄', 'huh0529@gmail.com'),
('gustjgus', 'dkfji3j388', '거꾸로해도현서현', 'gustjgus@ssafy.com'),
('wjdrbgus8167', 'djfkj111', '정규식', 'wjdrbgus8167@gmail.com'),
('ansoo97', 'sadfsdaf22', '안쓰', 'ansoo97@daum.net'),
('dbsruddlf10', 'sadf45647', '경일', 'dbsruddlf10@daum.net'),
('wlgks7460', '34421dfaawer', '한잔하자', 'wlgks7460@ssafy.com'),
('wneotjd228', 'fa687aewf43', '대썽', 'wneotjd228@naver.com'),
('jongmin0821', 'dsf54we8743', '익산코드왕', 'jongmin0821@ssafy.com'),
('ruddms1522', 'f1733', '중금', 'ruddms1522@gmail.com'),
('vrooming13', '21sd7f8s', '브루밍', 'vrooming13@ssafy.com'),
('dkqrty', '34387fsd', '명예제주도민', 'dkqrty@ssafy.com'),
('try0047', '34387fsdf', '시도', 'try0047@ssafy.com'),
('pbj2577', 'h3432141', '폭탄주', 'pbj2577@naver.com'),
('gmlwn1636', 'uio21ysdyf', '희주97', 'gmlwn1636@gmail.com'),
('scnu4433', '3jh4ud', '공부할가연', 'scnu4433@gmail.com'),
('rmfls4359', '3u4h6h', '팜므파탈', 'rmfls4359@daum.net');
