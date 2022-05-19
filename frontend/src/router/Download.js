import axios from "axios";
import { useEffect, useState } from "react";
import shortlogo from "../res/img/logo_temp.png";
import landing from "../res/img/landing-removebg.png";
import mt_detail from "../res/img/mt_detail.jpg";
import ranking_list from "../res/img/ranking_list.jpg";
import matching_list from "../res/img/matching_list.jpg";
import matching_detail from "../res/img/matching_detail.jpg";
import mypage from "../res/img/mypage.jpg";
import plogging_result from "../res/img/plogging_result.jpg";

function Download() {
  const [userNum, setUserNum] = useState(0);
  const [dist, setDist] = useState(0);
  const welcome = () => {
    axios.get("https://k6a306.p.ssafy.io/api/plogging/welcome").then((res) => {
      setUserNum(res.data.numberOfUsers);
      setDist(res.data.totalDistance);
    });
  };
  const mobileVersion = () => {
    document.location.href = "https://k6a306.p.ssafy.io/m";
  };
  useEffect(() => {
    welcome();
    if (window.outerWidth < 800 || window.innerWidth < 800) {
      mobileVersion();
    }
  }, []);
  return (
    <div style={{ fontFamily: "'Noto Sans KR', sans-serif" }}>
      <div style={{ position: "sticky", top: "0", height: "10vh", backgroundColor: "white" }}>
        <div style={{ margin: "auto 15vw", height: "10vh", display: "flex", justifyContent: "space-between" }}>
          <a style={{ margin: "auto 0", fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io"}>
            <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around" }}>
              <img src={shortlogo} alt="" style={{ height: "7vh" }} />
              <div style={{ margin: "auto 1vw", fontSize: "3vh", fontWeight: "700" }}>Forest Keeper</div>
            </div>
          </a>
          <div style={{ margin: "auto 0", display: "flex", justifyContent: "space-around", width: "15vw" }}>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
              DOWNLOAD
            </a>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://lab.ssafy.com/s06-final/S06P31A306"}>
              DOCS
            </a>
          </div>
        </div>
      </div>
      <div style={{ padding: "2vh 12vw", height: "50vh", backgroundColor: "#B8DEB3", display: "flex" }}>
        <div style={{ margin: "7vh 5vh", width: "50vw" }}>
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>등산과 플로깅의 만남</div>
            <div style={{ margin: "0", fontSize: "5vh", fontWeight: "700", color: "black" }}>Forest Keeper</div>
            <button
              style={{
                backgroundColor: "#37CD8D",
                border: "none",
                borderRadius: "2rem",
                width: "15vw",
                height: "6vh",
                color: "white",
                margin: "7vh 0",
              }}
              onClick={() => (document.location.href = `/`)}
            >
              <a style={{ margin: "auto 0", fontWeight: "700", textDecoration: "none", color: "white" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
                <div style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "white" }}>동행하기</div>
              </a>
            </button>
            <div style={{ marginTop: "0vh" }}>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>지금 </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{userNum}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>명이 </span>
              <span style={{ margin: "0", fontSize: "3vh", fontWeight: "700", color: "black" }}>{dist}</span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>km를 걸으며 </span>
              <span style={{ margin: "0", fontSize: "2.5vh", fontWeight: "500", color: "black" }}>환경을 살리고 있습니다!</span>
            </div>
          </div>
        </div>
        <img src={landing} alt="" style={{ float: "right" }} />
      </div>
      <div style={{ height: "55vh", backgroundColor: "white", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "5vh 0 0 10vw", width: "35vw" }}>
          <img src={mt_detail} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={ranking_list} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
        <div style={{ marginRight: "20vw", padding: "15vh 0", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>산의 정보를 찾아보고</div>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>플로깅 챌린지에 참여해보세요.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>숲지기 어플에선 산의 정보를 찾을 수 있고.</div>
          <div style={{ fontSize: "2vh" }}>명예의 전당에서 누가 플로깅을 많이 했나 확인할 수 있어요.</div>
          <div style={{ fontSize: "2vh" }}>플로깅 횟수로도, 경험치로도 확인할 수 있답니다.</div>
        </div>
      </div>
      <div style={{ height: "55vh", backgroundColor: "#EAEAEA", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "15vh 0 0 10vw", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>함께 플로깅할 친구를 찾아보세요.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>혼자 산에 가서 플로깅하기엔 조금 부끄럽다고요?</div>
          <div style={{ fontSize: "2vh" }}>함께 플로깅할 친구를 모집할 수 있답니다.</div>
          <div style={{ fontSize: "2vh" }}>원하는 시간, 원하는 동료와 함께 줍줍!</div>
        </div>
        <div style={{ marginRight: "20vw", padding: "5vh 0", width: "30vw" }}>
          <img src={matching_list} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={matching_detail} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
      </div>
      <div style={{ height: "55vh", backgroundColor: "white", display: "flex" }}>
        <div style={{ margin: "0 10vw", padding: "5vh 0 0 10vw", width: "35vw" }}>
          <img src={mypage} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px" }} />
          <img src={plogging_result} alt="" style={{ width: "10vw", boxShadow: "0px 5px 10px 2px darkgray", borderRadius: "10px", margin: "-8vh" }} />
        </div>
        <div style={{ marginRight: "20vw", padding: "15vh 0", width: "45vw" }}>
          <div style={{ fontSize: "3.5vh", fontWeight: "700" }}>플로깅 인증하고 기록을 남겨보세요.</div>
          <div style={{ fontSize: "2vh", marginTop: "3vh" }}>어떤 쓰레기를 주웠나 인증 고고!</div>
          <div style={{ fontSize: "2vh" }}>주운 쓰레기의 양과 종류에 따라 경험치가 부여된답니다.</div>
          <div style={{ fontSize: "2vh" }}>마이페이지에서 지난 기록까지 한눈에 확인해보자구요~</div>
        </div>
      </div>
      <div style={{ height: "18vh", backgroundColor: "#B8DEB3" }}>
        <div style={{ margin: "0 15vw", paddingTop: "1vh", height: "2vh", display: "flex", justifyContent: "space-between" }}>
          <div style={{ display: "flex", justifyContent: "space-around" }}>
            <img src={shortlogo} alt="" style={{ height: "7vh" }} />
            <div style={{ margin: "1vh 1vh", fontSize: "3vh", fontWeight: "700" }}>Forest Keeper</div>
          </div>
          <div style={{ marginTop: "12vh" }}>© 2022 ForestKeeper All rights reserved.</div>
          <div style={{ margin: "1vh 0", display: "flex", justifyContent: "space-around", width: "15vw" }}>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://k6a306.p.ssafy.io/api/apk"}>
              DOWNLOAD
            </a>
            <a style={{ fontWeight: "700", textDecoration: "none", color: "black" }} href={"https://lab.ssafy.com/s06-final/S06P31A306"}>
              DOCS
            </a>
          </div>
        </div>
      </div>
    </div>
  );
}
export default Download;
