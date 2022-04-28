import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Plogging from "./router/main/plogging/Plogging";
import Signup from "./router/accounts/Signup";
import Login from "./router/accounts/Login";
import RecordDetail from "./router/accounts/mypage/RecordDetail";
import Settings from "./router/accounts/mypage/Settings";
import Index from "./router/main/Home/Index";
import SearchList from "./router/main/Home/SearchList";
import Detail from "./router/main/Home/Detail";
function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/accounts/signup" element={<Signup />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route
            path="/accounts/mypage/recorddetail"
            element={<RecordDetail />}
          />
          <Route path="/accounts/mypage/settings" element={<Settings />} />
          <Route path="/detail/:mountainCode" element={<Detail />} />
          <Route path="/" element={<Index />}>
            <Route index element={<Home />} />
            <Route path="plogging" element={<Plogging />} />
            <Route path="accounts/mypage" />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
export default App;
