import { dividerClasses } from '@mui/material';
import React, { useState, useEffect } from 'react';
import "../styles/Infor.css";
import RFID_service from '../services/RFID_service';

function Infor(props) {
  const [RFIDs, setRFID] = useState([]);
  const [RFIDsCount, setRFIDsCount] = useState(0); // Biến lưu số lượng giá trị

  useEffect(() => {
    RFID_service.getRFID_List().then((res) => {
      setRFID(res.data);
      setRFIDsCount(res.data.length); // Cập nhật số lượng giá trị
    });
  }, []);

  return (
    <div className='Infor'>
      <div className="table">
        <table>
          <thead>
            <tr>
              <th>id</th>
              <th>IDx</th>
              <th>timeIn</th>
            </tr>
          </thead>
          <tbody>
            {RFIDs.map((data) => (
              <tr key={data.id}>
                <td>{data.IDx}</td>
                <td>{data.timeIn}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <div className="slot">
        {/* <p>{RFIDsCount}</p> Hiển thị số lượng giá trị */}
        {RFIDsCount > 8  ? <p>Hết slot</p> : RFIDsCount}
      </div>
    </div>

  );
}

export default Infor;