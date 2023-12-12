import axios from 'axios';

const user_API_BASE_URL = "http://localhost:8082/user";

class USER_service {

    getUserDTO(){
        return axios.get("http://localhost:8082/user/listDTO");
    }

    getUserById(userId) {
        return axios.get("http://localhost:8082/user/${userId}");
    }

    deleteUserDTO(userId) {
        return axios.delete("http://localhost:8082/user/deletex/${userId}");
    }

    login(loginDTO) {
        return axios.post("http://localhost:8082/user/login", loginDTO);
    }
    addSignUp_db(userDTO){
        return axios.post("http://localhost:8082/user/saveUserDTO", userDTO);
    }
    addUSER(userDTO){
        return axios.post("http://localhost:8082/user/saveUser", userDTO);
    }
}

export default new USER_service()