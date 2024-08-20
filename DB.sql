DROP DATABASE IF EXISTS `24_08_Spring`;
CREATE DATABASE `24_08_Spring`;
USE `24_08_Spring`;

CREATE TABLE article(
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      title CHAR(100) NOT NULL,
      `body` TEXT NOT NULL
);

CREATE TABLE `member`(
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      loginId CHAR(30) NOT NULL,
      loginPw CHAR(100) NOT NULL,
      `authLevel` SMALLINT(2) UNSIGNED DEFAULT 3 COMMENT '권한 레벨(3 = 일반, 7 = 관리자)',
      `name` CHAR(20) NOT NULL,
      nickname CHAR(20) NOT NULL,
      cellphoneNum CHAR(20) NOT NULL,
      email CHAR(50) NOT NULL,
      delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '탈퇴 여부(0 = 탈퇴 전, 1 = 탈퇴 후)',
      delDate DATETIME COMMENT '탈퇴 날짜'
);

# 게시판(board) 테이블 생성
CREATE TABLE board(
      id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
      regDate DATETIME NOT NULL,
      updateDate DATETIME NOT NULL,
      `code` CHAR(50) NOT NULL UNIQUE COMMENT 'notice(공지사항) free(자유) QnA(질의응답) ...',
      `name` CHAR(20) NOT NULL UNIQUE COMMENT '게시판 이름',
      delStatus TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '삭제 여부(0 = 삭제 전, 1 = 삭제 후)',
      delDate DATETIME COMMENT '삭제 날짜'
);

## 게시글 테스트 데이터 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목4',
`body` = '내용4';

## 회원 테스트 데이터 생성
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`authLevel` = 7,
`name` = '관리자',
nickname = '관리자',
cellphoneNum = '01043214321',
email = 'abcd@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test1',
loginPw = 'test1',
`name` = '회원1_이름',
nickname = '회원1_닉네임',
cellphoneNum = '01012341234',
email = 'abc@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test2',
loginPw = 'test2',
`name` = '회원2_이름',
nickname = '회원2_닉네임',
cellphoneNum = '01056785678',
email = 'abcde@gmail.com';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'test3',
loginPw = 'test3',
`name` = '회원3_이름',
nickname = '회원3_닉네임',
cellphoneNum = '01009870987',
email = 'abcdef@gmail.com';

ALTER TABLE article ADD COLUMN memberId INT(10) UNSIGNED NOT NULL AFTER updateDate;

UPDATE article
SET memberId = 2
WHERE id IN(1, 2);

UPDATE article
SET memberId = 3
WHERE id IN(3, 4);

## 게시판(board) 테스트 데이터 생성
INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'NOTICE',
`name` = '공지사항';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'FREE',
`name` = '자유';

INSERT INTO board
SET regDate = NOW(),
updateDate = NOW(),
`code` = 'QnA',
`name` = '질의응답';

ALTER TABLE article ADD COLUMN boardId INT(10) UNSIGNED NOT NULL AFTER memberId;

UPDATE article
SET boardId = 1
WHERE id IN(1, 2);

UPDATE article
SET boardId = 2
WHERE id = 3;

UPDATE article
SET boardId = 3
WHERE id = 4;

#################################################################

SELECT *
FROM article
ORDER BY id DESC;

SELECT * FROM board;

SELECT *
FROM `member`;

###############################################################################

SELECT CEILING(RAND() * 3);

SELECT CEILING(RAND() * 4);

## 게시글 테스트 데이터 대량 생성 v1 (2배씩 증가)
INSERT INTO article
(
    regDate, updateDate, memberId, boardId, title, `body`
)
SELECT NOW(), NOW(), FLOOR(RAND() * 2) + 2, FLOOR(RAND() * 3) + 1, CONCAT('제목__', RAND()), CONCAT('내용__', RAND())
FROM article;

## 게시글 테스트 데이터 대량 생성 v2
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = FLOOR(RAND() * 2 + 2),
boardId = FLOOR(RAND() * 3 + 1),
title = CONCAT('제목__', RAND()),
`body` = CONCAT('내용__', RAND());

SHOW FULL COLUMNS FROM `member`;

SELECT COUNT(*) FROM article WHERE boardId = 1;
SELECT COUNT(*) FROM article WHERE boardId = 2;
SELECT COUNT(*) FROM article WHERE boardId = 3;