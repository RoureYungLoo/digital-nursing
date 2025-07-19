import request from '@/utils/request'

// 查询护理项目列表
export function listItem(query) {
  return request({
    url: '/nursing/item/list',
    method: 'get',
    params: query
  })
}

// 查询护理项目详细
export function getItem(id) {
  return request({
    url: '/nursing/item/' + id,
    method: 'get'
  })
}

// 新增护理项目
export function addItem(data) {
  return request({
    url: '/nursing/item',
    method: 'post',
    data: data
  })
}

// 修改护理项目
export function updateItem(data) {
  return request({
    url: '/nursing/item',
    method: 'put',
    data: data
  })
}

// 删除护理项目
export function delItem(id) {
  return request({
    url: '/nursing/item/' + id,
    method: 'delete'
  })
}
