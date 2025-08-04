package com.luruoyang.nursing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.entity.domain.Room;
import com.luruoyang.nursing.entity.vo.FloorRoomDeviceInfo;
import com.luruoyang.nursing.mapper.RoomMapper;
import com.luruoyang.nursing.service.IRoomService;
import com.luruoyang.nursing.entity.vo.RoomVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 房间Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-26
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {
  @Autowired
  private RoomMapper roomMapper;

  /**
   * 查询房间
   *
   * @param id 房间主键
   * @return 房间
   */
  @Override
  public Room selectRoomById(Long id) {
    return getById(id);
  }

  /**
   * 查询房间列表
   *
   * @param room 房间
   * @return 房间
   */
  @Override
  public List<Room> selectRoomList(Room room) {
    return roomMapper.selectRoomList(room);
  }

  /**
   * 新增房间
   *
   * @param room 房间
   * @return 结果
   */
  @Override
  public int insertRoom(Room room) {
    return save(room) ? 1 : 0;
  }

  /**
   * 修改房间
   *
   * @param room 房间
   * @return 结果
   */
  @Override
  public int updateRoom(Room room) {
    return updateById(room) ? 1 : 0;
  }

  /**
   * 批量删除房间
   *
   * @param ids 需要删除的房间主键
   * @return 结果
   */
  @Override
  public int deleteRoomByIds(Long[] ids) {
    return removeByIds(Arrays.asList(ids)) ? 1 : 0;
  }

  /**
   * 根据楼层 id 获取房间视图对象列表
   *
   * @param floorId
   * @return
   */
  @Override
  public List<RoomVo> getRoomsByFloorId(Long floorId) {
    return roomMapper.selectByFloorId(floorId);
  }


  /**
   * 获取所有房间（负责老人）
   *
   * @param floorId
   * @return
   */
  @Override
  public List<RoomVo> getRoomsWithNurByFloorId(Long floorId) {
    return roomMapper.selectByFloorIdWithNur(floorId);
  }

  @Override
  public RoomVo getRoomFloorPriceById(Long roomId) {
    return roomMapper.getRoomFloorPriceById(roomId);
  }

  /**
   * 根据楼层获取该楼层所有房间智能设备的最新数据
   *
   * @param floorId 楼层ID
   * @return 该楼层所有房间智能设备的最新数据
   */
  @Override
  public List<FloorRoomDeviceInfo> getRoomsWithDeviceByFloorId(Long floorId) {
    List<FloorRoomDeviceInfo> frdi = roomMapper.getRoomsWithDeviceByFloorId(floorId);


    return frdi;
  }
}
