import "./App.css";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Menu from "./pages/Menu";
import About from "./pages/About";
import Login from "./pages/Login";
import Sign_up from "./pages/Sign_up";
import Infor from "./pages/Infor";
import List_user from "./pages/List_userDTO";
import Map from "./pages/Map";
import { BrowserRouter ,Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
      <Navbar />
      <Routes>
      
        <Route path="/" element={<Home />}/>
        <Route path="menu" element={<Menu />} />
        <Route path="login" element={<Login />} />
        <Route path="about" element={<About />} />
        <Route path="sign_up" element={<Sign_up />} />
        <Route path="infor" element={<Infor />}/>
        <Route path="list_user" element={<List_user />}/>
        <Route path="map" element={<Map />}/>
      </Routes>
      <Footer />
      </BrowserRouter>
    </div>
  );
}

export default App;