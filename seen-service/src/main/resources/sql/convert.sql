-- 数据表转换

-- photo to file and main_photo
insert into file(storage_type, path, name)
select
    1 as storage_type,path, SUBSTRING_INDEX(path, '/', -1)
from photo_discard;

insert into main_photo(id, file_id)
select a.id,b.id
from file b ,photo_discard a
where a.path = b.path