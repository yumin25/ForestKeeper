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
import TeamWrite from "./router/main/Home/MtnDetail/TeamWrite";
import TeamDetail from "./router/main/Home/MtnDetail/TeamDetail";

import TeamList from "./router/main/Home/MtnDetail/TeamList";

import Landing from "./router/main/Home/Land";
import Download from "./router/Download";
import DownloadMobile from "./router/DownloadMobile";

function App() {
  const cors = require("cors");
  return (
    <>
      <Router>
        <Routes>
          <Route exact path="/" element={<Download />} />
          <Route exact path="/m" element={<DownloadMobile />} />
          <Route path="/accounts/signup" element={<Signup />} />
          <Route path="/accounts/login" element={<Login />} />
          <Route path="/landing" element={<Landing />} />

          <Route path="/accounts/mypage/recorddetail" element={<RecordDetail />} />

          <Route path="/trash" element={<Trash />} />

          <Route path="/recommend" element={<Temp />} />
          <Route path="/accounts/mypage/settings" element={<Settings />} />
          <Route path="/detail/:mountainCode" element={<Detail />} />
          <Route path="/articleDetail" element={<ArticleDetail />} />

          <Route path="/articleWrite/:mountainCode" element={<Write />} />
          <Route path="/teamWrite/:mountainCode" element={<TeamWrite />} />
          <Route path="/teamList/:mountainCode" element={<TeamList />} />
          <Route path="/teamDetail" element={<TeamDetail />} />
          <Route path="/" element={<Index />}>
            <Route path="home" element={<Home />} />
            <Route path="plogging" element={<Plogging />} />
            <Route path="accounts/mypage" element={<MyPage />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
export default App;
