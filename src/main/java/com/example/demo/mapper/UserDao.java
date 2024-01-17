package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.entity.User;

@Mapper
public interface UserDao {
    
    /**
	 * 新增用户
	 *
	 * @param user 用户对象
	 * @return 新增成功记录条数
	 */
    int add(User user);

    /**
	 * 修改用户信息
	 *
	 * @param user 用户对象
	 * @return 修改成功记录条数
	 */
    int update(User user);

    /**
	 * 根据id获取用户
	 *
	 * @param id 用户id
	 * @return 用户对象
	 */
    User getById(Integer id);

    /**
	 * 根据用户名获取用户
	 *
	 * @param username 用户名
	 * @return 用户对象
	 */
    User getByUserName(String username);
}
