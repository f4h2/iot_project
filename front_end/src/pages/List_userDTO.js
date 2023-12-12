import { dividerClasses } from '@mui/material';
import React, { useState, useEffect } from 'react';
import "../styles/Infor.css";
import USER_service from "../services/USER_service";
import { useLocation } from 'react-router-dom';
// import axios from 'axios';

function List_userDTO(props) {
    const [users, setUser] = useState([]);


    const deleteUser = (id) => {
        USER_service.deleteUserDTO(id).then((res) => {
            setUser(users.filter((user) => user.id !== id));
        });
    };
    const addUSER = (users) => {
        USER_service.addUSER(users);
    };
    useEffect(() => {
        USER_service.getUserDTO().then((res) => {
            setUser(res.data);
        });
    }, []);


  return (
    <div className='List'>
        <div className="table_rigister">
            <table>
            <thead>
                <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Account</th>
                <th>Password</th>
                <th>Role</th>
                <th>Action</th>
                </tr>
            </thead>
            <tbody>
                {users.map((user_register) => (
                <tr 
                    key={user_register.id}>
                    <td>{user_register.Name}</td>
                    <td>{user_register.Account}</td>
                    <td>{user_register.Password}</td>
                    <td>{user_register.Role}</td>
                    <td>
                                    
                        <button
                            style={{ marginLeft: '10px' }}
                            onClick={() => deleteUser(user_register.id)}
                            className="btn btn-danger"
                        >
                            Delete
                        </button>
                        <button
                            style={{ marginLeft: '10px' }}
                            onClick={() =>addUSER(user_register)}
                            className="btn btn-info"
                        >
                            Add
                        </button>
                    </td>
                </tr>
                ))}
            </tbody>
            </table>
        </div>
        
    </div>


  );
}

export default List_userDTO;