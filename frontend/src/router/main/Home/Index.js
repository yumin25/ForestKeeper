import React from "react";
import Home from "./Home";
import Plogging from "../plogging/Plogging";
import MyPage from "../../accounts/mypage/MyPage";
import Bar from "./Bar";
import { Route, Routes } from "react-router-dom";

function Index() {
  return (
    <>
      <div>
        <Routes>
          <Route path="/home" exact element={<Home />} />
          <Route path="/plogging" exact element={<Plogging />} />
          <Route path="/accounts/mypage" exact element={<MyPage />} />
        </Routes>
        <Bar />
      </div>
    </>
  );
}
export default Index;
