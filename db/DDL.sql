--
--user_id id
--user_name 
--email 
--user_password 
--create_time 
DROP TABLE IF EXISTS "t_user";
CREATE TABLE "t_user" (
  "user_id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_name" TEXT,
	"email" TEXT,
  "user_password" TEXT,
  "create_time" TEXT
);

--
--comment_id id1
--user_id id
--user_name 
--content 
--comment_pid id0
--reply_id id
--reply_name 
--create_time 
DROP TABLE IF EXISTS "t_comment";
CREATE TABLE "t_comment" (
  "comment_id" integer NOT NULL PRIMARY KEY AUTOINCREMENT,
  "user_id" INTEGER,
	"user_name" TEXT,
  "content" TEXT,
	"comment_pid" integer,
	"reply_id" TEXT,
	"reply_name" TEXT,
  "create_time" text
);
INSERT INTO "main"."sqlite_sequence" (name, seq) VALUES ('t_comment', '1');