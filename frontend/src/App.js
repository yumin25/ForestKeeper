import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Login from "./router/accounts/Login";
import MyPage from "./router/accounts/mypage/MyPage";
import RecordDetail from "./router/accounts/mypage/RecordDetail";
import Settings from "./router/accounts/mypage/Settings";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/accounts/mypage" element={<MyPage />} />
          <Route path="/accounts/mypage/recorddetail" element={<RecordDetail />} />
          <Route path="/accounts/mypage/settings" element={<Settings />} />
        </Routes>
      </Router>
    </>
  );
}

export default App;
