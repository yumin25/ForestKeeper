import { useNavigate } from "react-router-dom";

function RecordDetail() {
  const navigate = useNavigate();
  const Logout = () => {
    window.localStorage.clear();
    navigate({
      pathname: "/accounts/login",
    });
  };

  return (
    <>
      <div style={{ display: "flex", margin: "1vh", justifyContent: "space-between" }}>
        <span className="material-icons-outlined" style={{ fontSize: "2rem", color: "#8E8E92" }} onClick={() => navigate(-1)}>
          arrow_back_ios
        </span>
        <div style={{ display: "flex", alignItems: "center", fontSize: "2.5vh", fontWeight: "700" }}>설정</div>
        <div style={{ visibility: "hidden" }}>설정</div>
      </div>
      <hr />
      {/* 작성글 */}
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>내가 쓴 글</div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>내가 쓴 댓글</div>
      <hr />
      <div style={{ visibility: "hidden" }}></div>
      <hr />
      {/* 설정 */}
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>공지사항</div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>의견 보내기</div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>
        서비스 이용 약관
      </div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>
        개인정보 보호정책
      </div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "500" }}>서비스 버전</div>
      <hr />
      <div style={{ visibility: "hidden" }}></div>
      <hr />
      {/* 로그아웃 and 탈퇴 */}
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "700" }} onClick={Logout}>
        로그아웃
      </div>
      <hr />
      <div style={{ margin: "1vh", height: "5vh", minHeight: "25px", display: "flex", alignItems: "center", fontWeight: "700", color: "red" }}>
        회원탈퇴
      </div>
      <hr />
    </>
  );
}

export default RecordDetail;
