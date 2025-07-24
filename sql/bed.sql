select f.name,
       f.code,

       r.code,
       r.sort,
       r.type_name,
       r.floor_id,

       b.bed_number,
       b.bed_status,
       b.sort,
       b.room_id
from floor f
         left join room r on f.id = r.floor_id
         left join bed b on r.id = b.room_id;

select f.id fid, f.name, r.id rid, r.code, b.id bid, b.bed_number
from floor f
         left join room r on f.id = r.floor_id
         left join bed b on r.id = b.room_id;

-- 根据床位状态查询所有楼层所有房间的床位信息
select f.id         fid,
       f.name       fname,

       r.id         rid,
       r.code       rcode,

       b.id         bid,
       b.bed_number bbn
from floor f
         left join room r on f.id = r.floor_id
         left join bed b on r.id = b.room_id
where b.bed_status = 0;

-- 根据房间id查询房间数据（楼层、房间、价格）

select f.name floorName,f.id floorId, r.id roomId, r.code, rt.price
from room r
         left join room_type rt on r.type_name = rt.name
         left join floor f on r.floor_id = f.id
where r.id = #{roomId}