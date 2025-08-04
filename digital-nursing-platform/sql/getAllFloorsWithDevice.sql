-- select f.id, f.code, f.name
select *
from floor f
         left join room r on r.floor_id = f.id
         left join bed b on b.room_id = r.id
    -- room device
         left join device rd
                   on rd.binding_location = r.id and rd.location_type = 1 and rd.physical_location_type = 1
    -- bed device
         left join device bd
                   on bd.binding_location = b.id and bd.location_type = 1 and bd.physical_location_type = 2
where (rd.id is not null or bd.id is not null)
group by f.id;

update bed
set bed_status=1
where id > 0;


select *
from room r
         left join bed b on b.room_id = r.id
         left join elder e on e.bed_id = b.id
    -- room device
         left join device rd on rd.binding_location = r.id and rd.location_type = 1 and rd.physical_location_type = 1
    -- bed device
         left join device bd on bd.binding_location = b.id and bd.location_type = 1 and bd.physical_location_type = 2
where floor_id = 3
  and (bd.id is not null or rd.id is not null)
group by b.id, r.id
