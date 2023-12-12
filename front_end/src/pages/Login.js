import ParkLeft from "../assets/park_left.jpg";
import "../styles/Login.css";
import React, { useState } from "react";
import USER_service from "../services/USER_service";

const Login = (props) => {
  const [user_tk, setAccount] = useState('');
  const [user_mk, setPass] = useState('');

  
  const login = (e) => {
    e.preventDefault();
    let LoginDTO = { user_tk,user_mk};
    console.log('user => ' + JSON.stringify(LoginDTO));
    USER_service.login(LoginDTO).then((res) => {
      const userId = res.data.id;
      const message = res.data.message;
      const role = res.data.Role;
      
      if (message === "Login Ok"  && role === "ROLE_USER") {
        const { history } = this.props;
        this.props.history.push("/menu");
        localStorage.setItem('res', JSON.stringify(res));
      } else if(message === "Login Ok"  && role === "ROLE_ADMIN"){
        const { history } = this.props;
        this.props.history.push("/list_dto");
        localStorage.setItem('res', JSON.stringify(res));
      }else {
        this.props.history.push("/login");
      }
    });
  };

 

  const login_PassHandler = (event) => {
    setPass(event.target.value);
  };

  const login_AccountHandler = (event) => {
    setAccount(event.target.value);
  };
  


  return (
    <div className="login">
      <div
        className="leftSide"
        style={{ backgroundImage: `url(${ParkLeft})` }}
      ></div>
      <div className="rightSide">
        <h1> Login to use our system</h1>

        <form id="login-form" method="POST">
          <label htmlFor="account">Account</label>
          <input name="account" placeholder="Enter account..." value={user_tk} type="text" onChange={login_PassHandler}/>
          <label htmlFor="password">Password</label>
          <input name="password" placeholder="Enter Password..." value={user_mk} type="text" onChange={login_AccountHandler}/>
          
          <button type="submit"> Send</button>
        </form>
        <button className="link-btn" onClick={login}>Already have an account? Submit here.</button>
      </div>
    </div>
  );
}

export default Login;