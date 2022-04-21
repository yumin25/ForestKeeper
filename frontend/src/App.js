import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Login from "./router/accounts/Login";
import MyPage from "./router/accounts/MyPage";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/accounts/mypage" element={<MyPage />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
