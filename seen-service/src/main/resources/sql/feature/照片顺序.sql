ALTER TABLE `user_introduce_photo` 
	DROP COLUMN `create_time`,
	CHANGE COLUMN `photo_id` `order_num` int NOT NULL COMMENT '顺序' AFTER `introduce_type`
;


insert user_introduce_photo_to_photo
select id, user_introduce_photo.`order_num`
from user_introduce_photo;
