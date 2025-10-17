package co.edu.unbosque.service;

import java.util.List;

import co.edu.unbosque.dto.UserDTO;

public class UserService implements CRUDOperation<UserDTO> {

	@Override
	public int create(UserDTO data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteById(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateById(Long id, UserDTO newData) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exist(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
