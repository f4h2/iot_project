import axios from 'axios';

const user_API_BASE_URL = "http://localhost:8082/map";

class Map_ser {
    getID_position(){
        return axios.get(user_API_BASE_URL + '/' + 'light_position')
    }
}

export default new Map_ser()