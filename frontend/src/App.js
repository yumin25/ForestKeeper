import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Login from "./router/accounts/Login";
import RecordDetail from "./router/accounts/mypage/RecordDetail";
import Settings from "./router/accounts/mypage/Settings";
import Index from "./router/main/Home/Index";

function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/accounts/mypage/recorddetail" element={<RecordDetail />} />
          <Route path="/accounts/mypage/settings" element={<Settings />} />
          <Route path="/" element={<Index />}>
            <Route index element={<Home />} />
            <Route path="plogging" />
            <Route path="/accounts/mypage" />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
export default App;
