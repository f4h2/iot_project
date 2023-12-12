import axios from 'axios';

const user_API_BASE_URL = "http://localhost:8082/RFID";

class RFID_service {
    getRFID_List(){
        return axios.get(user_API_BASE_URL + '/' + 'listRFID')
    }
}

export default new RFID_service()