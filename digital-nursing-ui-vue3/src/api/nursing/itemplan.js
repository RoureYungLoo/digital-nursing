import request from '@/utils/request'

// 查询护理计划和项目关联列表
export function listItemplan(query) {
  return request({
    url: '/nursing/itemplan/list',
    method: 'get',
    params: query
  })
}

// 查询护理计划和项目关联详细
export function getItemplan(id) {
  return request({
    url: '/nursing/itemplan/' + id,
    method: 'get'
  })
}

// 新增护理计划和项目关联
export function addItemplan(data) {
  return request({
    url: '/nursing/itemplan',
    method: 'post',
    data: data
  })
}

// 修改护理计划和项目关联
export function updateItemplan(data) {
  return request({
    url: '/nursing/itemplan',
    method: 'put',
    data: data
  })
}

// 删除护理计划和项目关联
export function delItemplan(id) {
  return request({
    url: '/nursing/itemplan/' + id,
    method: 'delete'
  })
}
