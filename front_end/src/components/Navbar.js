import React, { useState } from "react";
import Logo from "../assets/Logo.jpg";
import { Link } from "react-router-dom";
import ReorderIcon from "@mui/icons-material/Reorder";
import "../styles/Navbar.css";

import {
  useNavigate
} from 'react-router-dom';

function Navbar() {

  const auth = localStorage.getItem('res');
  const navigate = useNavigate();
  const logout = () => {
    localStorage.clear();
    navigate('/signup')
  }


  const [openLinks, setOpenLinks] = useState(false);

  const toggleNavbar = () => {
    setOpenLinks(!openLinks);
  };
  return (
    <div className="navbar">
      
          <div className="leftSide" id={openLinks ? "open" : "close"}>
            <img src={Logo} />
            <div>
              {
                auth?
                  <div className="hiddenLinks">
                    <Link to="/"> Home </Link>
                    <Link to="/menu"> Menu </Link>
                    <Link to="/about"> About </Link>
                    <Link to="/login"> Login </Link>
                    <Link to="/sign_up"> Sign up </Link>
                    <Link to="/infor"> Infor </Link>
                    <Link to="/map"> Map </Link>
                    <Link onClick={logout} to ="/sign_up" > Logout ({JSON.parse(auth).name}) </Link>
                  </div>
                  : 
                  <div className="hiddenLinks nav_x">
                    <Link to="/sign_up"> Sign up </Link>
                    <Link to="/login"> Login </Link>
                  </div>
              }
            </div>
          </div>
          <div>
            {
              auth ?
                <div className="rightSide">
                  <Link to="/"> Home </Link>
                  <Link to="/menu"> Menu </Link>
                  <Link to="/about"> About </Link>
                  <Link to="/login"> Login </Link>
                  <Link to="/sign_up"> Sign up </Link>
                  <Link to="/infor"> Infor </Link>
                  <Link to="/map"> Map </Link>
                  <Link onClick={logout} to ="/sign_up" > Logout ({JSON.parse(auth).name}) </Link>
                  <button onClick={toggleNavbar}>
                    <ReorderIcon />
                  </button>
                </div>
                : 
                <div className="rightSide nav_rightSide">
                    <Link to="/sign_up"> Sign up </Link>
                    <Link to="/login"> Login </Link>
                </div>
            }
          </div>
    </div>
  );
}

export default Navbar;