import React, { useState } from "react";
import USER_service from "../services/USER_service";

const Sign_up = (props) => {
    const [user_tk, setAccount] = useState('');
    const [user_mk, setPass] = useState('');
    const [user_ten, setName] = useState('');
    const [role,setRole] = useState('');
    const [user_id,setUser_id] = useState('');

    

      const saveUserDTO_DB = (e) => {
        e.preventDefault();
        let user = { user_id,user_ten, user_tk,user_mk, role };
        console.log(user);
        USER_service.addSignUp_db(user);
      };

      const changeNameHandler = (event) => {
        setName(event.target.value);
      };
    
      const changePassHandler = (event) => {
        setPass(event.target.value);
      };
    
      const changeAccountHandler = (event) => {
        setAccount(event.target.value);
      };
      const changeRoleHandler = (event) => {
        setRole(event.target.value);
      };
    
      // const cancel = () => {
      //   props.history.push('/employees');
      // };
   

    return (
        <div className="auth-form-container">
            <h2>Sign up</h2>
        <form className="Sign_up-form" onSubmit={saveUserDTO_DB}>
            <label htmlFor="name">Full name</label>
                <input
                    placeholder="Full Name"
                    name="name"
                    className="form-control"
                    value={user_ten}
                    onChange={changeNameHandler}
                  />
            {/* <input value={name} name="name" onChange={(e) => setName(e.target.value)} id="name" placeholder="full Name" /> */}
            <label htmlFor="account">account</label>
                <input
                    placeholder="account"
                    name="account"
                    className="form-control"
                    value={user_tk}
                    onChange={changeAccountHandler}
                  />
            {/* <input value={account} onChange={(e) => setAccount(e.target.value)}type="account" placeholder="account" id="account" name="account" /> */}
            <label htmlFor="password">password</label>
                <input
                    placeholder="********"
                    name="password"
                    className="form-control"
                    value={user_mk}
                    onChange={changePassHandler}
                    type="password"
                  />
            
            {/* <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="********" id="password" name="password" /> */}
            <label htmlFor="role">role</label>
                <input
                    placeholder="********"
                    name="role"
                    className="form-control"
                    value={role}
                    onChange={changeRoleHandler}
                  />
            <button type="submit">Submit</button>
        </form>
        <button className="link-btn" onClick={saveUserDTO_DB}>Already have an account? Submit here.</button>
    </div>
    )
}
export default Sign_up;