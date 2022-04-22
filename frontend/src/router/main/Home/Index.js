import React from "react";
import Home from "./Home";
import Plogging from "./Plogging";
import User from "./User";
import Bar from "./Bar";
import { Route, Routes } from "react-router-dom";
function Index() {
  return (
    <>
      <div>
        <Routes>
          <Route index path="/" element={<Home />} />
          <Route path="/plogging" exact element={<Plogging />} />
          <Route path="/user" exact element={<User />} />
        </Routes>
        <Bar />
      </div>
    </>
  );
}
export default Index;
