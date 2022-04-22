import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Login from "./router/accounts/Login";
import Plogging from "./router/main/Home/Plogging";
import User from "./router/main/Home/User";
import Index from "./router/main/Home/Index";
function App() {
  return (
    <>
      <Router>
        <Routes>
          {/* <Route path="/" element={<Home />} /> */}
          {/* <Route path="/" element={<Index />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/plogging" element={<Plogging />} />
          <Route path="/user" element={<User />}></Route> */}
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/" element={<Index />}>
            <Route index element={<Home />} />
            <Route path="plogging" />
            <Route path="user" />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
export default App;
