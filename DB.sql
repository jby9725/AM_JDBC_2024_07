-- 문자열 붙이기
SELECT CONCAT('제목' , ' 1');
-- 랜덤 수 출력 (범위 : 0~1)
SELECT RAND();

SELECT SUBSTRING(RAND() * 1000 FROM 1 FOR 2);

#####################################################################
## 구조 변경 (author 추가)
CREATE TABLE article (
    `id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `regDate` DATETIME NOT NULL,
    `updateDate` DATETIME NOT NULL,
    `author` INT UNSIGNED NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `body` TEXT NOT NULL
);

-- `member` table 생성
CREATE TABLE `member` (
    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    `regDate`	DATETIME NOT NULL,
    `userId` VARCHAR(100) NOT NULL UNIQUE,
    `password` VARCHAR(100) NOT NULL,
    `nickname` VARCHAR(100) NOT NULL
);

INSERT INTO `member` (`regDate`, `userId`, `password`, `nickname`)
VALUES
    (NOW(), 'test01', 'test01', '홍길동'),
    (NOW(), 'test02', 'test02', '홍길순'),
    (NOW(), 'test03', 'test03', '임꺽정');

-- 문자열 붙이기 + 랜덤 수 출력
INSERT INTO article
SET regDate = NOW(),
    updateDate = NOW(),
    author = 3,
    title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
    `body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));


SELECT * FROM article;
SELECT * FROM `member`;

## select ALL
SELECT A.*, M.nickname
FROM article A
INNER JOIN `member` M
ON A.author = M.id
ORDER BY A.id DESC;

## id 옵션 추가해서 select
SELECT A.*, M.nickname
FROM article A
INNER JOIN `member` M
ON A.author = M.id
WHERE A.id = 1;