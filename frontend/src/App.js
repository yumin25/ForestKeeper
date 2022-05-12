import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./router/main/Home/Home";
import Plogging from "./router/main/plogging/Plogging";
import Signup from "./router/accounts/Signup";
import Login from "./router/accounts/Login";
import RecordDetail from "./router/accounts/mypage/RecordDetail";
import Settings from "./router/accounts/mypage/Settings";
import Index from "./router/main/Home/Index";
import SearchList from "./router/main/Home/SearchList";
import Detail from "./router/main/Home/MtnDetail/Detail";
import ArticleDetail from "./router/main/Home/MtnDetail/ArticleDetail";
import Write from "./router/main/Home/MtnDetail/Write";
import Trash from "./router/main/Home/Trash";
import Temp from "./router/main/Home/Temp";
import MyPage from "./router/accounts/mypage/MyPage";
function App() {
  return (
    <>
      <Router>
        <Routes>
          <Route path="/accounts/signup" element={<Signup />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/accounts/mypage/recorddetail" element={<RecordDetail />} />

          <Route path="/trash" element={<Trash />} />

          <Route path="/accounts/mypage/settings" element={<Settings />} />
          <Route path="/detail/:mountainCode" element={<Detail />} />
          <Route path="/articleDetail" element={<ArticleDetail />} />
          <Route path="/articleWrite/:mountainCode" element={<Write />} />
          <Route path="/" element={<Index />}>
            <Route index element={<Home />} />
            <Route path="plogging" element={<Plogging />} />
            <Route path="accounts/mypage" element={<MyPage />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
export default App;
