import { useEffect, useState, useRef } from "react";
import { useNavigate } from "react-router-dom";
import { connect } from "react-redux";
import Send from "../../../config/Send";
import File from "../../../config/File";
import Slider from "react-slick";
import "../../../../node_modules/slick-carousel/slick/slick.css";
import "../../../../node_modules/slick-carousel/slick/slick-theme.css";
import { RenderAfterNavermapsLoaded, NaverMap, Marker, Polyline } from "react-naver-maps";

function RecordDetail() {
  const fileInput = useRef(null);
  const [image, setImage] = useState();
  const navigate = useNavigate();
  const [detail, setDetail] = useState({});
  const [centerX, setCenterX] = useState();
  const [centerY, setCenterY] = useState();
  const getDetail = (ploggingId) => {
    Send.get(`/plogging/${ploggingId}`, {
      params: {
        ploggingId: localStorage.getItem("ploggingId"),
      },
    })
      .then((res) => {
        setDetail(res.data);
        setCenterY(res.data.coords[parseInt((res.data.coords.length - 1) / 2)].y);
        setCenterX(res.data.coords[parseInt((res.data.coords.length - 1) / 2)].x);
      })
      .catch((e) => {
        console.log(e);
      });
  };
  const delId = (e) => {
    e.preventDefault();
    localStorage.removeItem("ploggingId");
  };
  const onImageHandler = (e) => {
    if (e.target.files[0]) {
      setImage(e.target.files[0]);
    }
  };
  const certify = () => {
    const formData = new FormData();
    const id = localStorage.getItem("ploggingId");
    formData.append("image", image);
    if (image) {
      File.post(`/plogging/ai?ploggingId=${id}`, formData).then((res) => {
        // console.log(res.data);
        getDetail(localStorage.getItem("ploggingId"));
      });
    }
  };

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };
  useEffect(() => {
    getDetail(localStorage.getItem("ploggingId"));
  }, []);

  useEffect(() => {
    certify();
  }, [image]);
  console.log(detail);
  return (
    <>
      <div
        style={{ display: "flex", margin: "1vh" }}
        onClick={(e) => {
          navigate("/accounts/mypage");
          delId(e);
        }}
      >
        <span className="material-icons-outlined" style={{ fontSize: "2rem", color: "#8E8E92" }}>
          arrow_back_ios
        </span>
        <span style={{ fontSize: "7vw", fontWeight: "700", color: "#8E8E92", marginLeft: "3vw" }}>플로깅 기록</span>
      </div>
      {/* 기록 */}
      <div style={{ display: "flex", flexDirection: "column", alignItems: "center", margin: "7vh 1vw 0 1vw", height: "75vh" }}>
        <>
          <div
            style={{
              marginBottom: "2vh",
              width: "80vw",
              height: "75vh",
              backgroundColor: "#EAF9E6",
              borderRadius: "10px",
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
              paddingBottom: "5vh",
            }}
          >
            <div style={{ margin: 0, width: "80vw", display: "flex" }}>
              <div style={{ margin: "0 auto", width: "70vw", display: "flex", flexDirection: "column", justifyContent: "space-evenly" }}>
                <div style={{ margin: "1vh 0", fontSize: "10vw", color: "#8ABC9A", fontWeight: "700" }}>{detail.date}</div>
                <div style={{ display: "flex" }}>
                  <div style={{ margin: "1vh 0", fontSize: "8vw", color: "#8ABC9A", fontWeight: "700" }}>{detail.mountainName}</div>
                  {detail.exp >= 1000 ? (
                    <button
                      type="input"
                      style={{
                        margin: "1.5vh 2vw",
                        color: "#8E8E92",
                        backgroundColor: "#B8DEB3",
                        border: "none",
                        borderRadius: "10%",
                        width: "20vw",
                        height: "4vh",
                      }}
                    >
                      인증완료
                    </button>
                  ) : (
                    <button
                      style={{
                        margin: "1.5vh 2vw",
                        color: "#8E8E92",
                        backgroundColor: "#CACACA",
                        border: "none",
                        borderRadius: "10%",
                        width: "20vw",
                        height: "4vh",
                      }}
                      onClick={() => {
                        fileInput.current.click();
                      }}
                    >
                      <form encType="multipart/form-data">
                        <input
                          ref={fileInput}
                          type="file"
                          style={{ display: "none" }}
                          className="imgInput"
                          id="profile_img"
                          accept="image/*"
                          name="file"
                          onChange={onImageHandler}
                          multiple
                        />
                      </form>
                      인증하기
                    </button>
                  )}
                </div>
                <div style={{ display: "flex", justifyContent: "space-around", color: "#8E8E92" }}>
                  <div style={{ marginTop: "1vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ margin: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>거리</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.distance}km</p>
                  </div>
                  <div style={{ marginTop: "1vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ margin: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>시간</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.time}</p>
                  </div>
                  <div style={{ marginTop: "1vh", display: "flex", flexDirection: "column", alignItems: "center" }}>
                    <p style={{ margin: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>경험치</p>
                    <p style={{ marginTop: 0, fontSize: "5vw", fontWeight: "700", color: "#8E8E92" }}>{detail.exp}exp</p>
                  </div>
                </div>
              </div>
            </div>
            {detail.imagePath ? (
              <div style={{ width: "65vw", height: "45vh" }}>
                <Slider {...settings}>
                  <div>
                    <RenderAfterNavermapsLoaded
                      ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
                      error={<p>Maps Load Error</p>}
                      loading={<p>Maps Loading...</p>}
                    >
                      <NaverMap
                        mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
                        style={{
                          width: "65vw", // 네이버지도 가로 길이
                          height: "45vh", // 네이버지도 세로 길이
                          position: "relative",
                          zIndex: 1,
                        }}
                        center={{ lat: centerY, lng: centerX }} // 지도 초기 위치
                        defaultZoom={13} // 지도 초기 확대 배율
                        draggable={false}
                        scrollWheel={false}
                      >
                        <Polyline path={detail.coords} strokeColor={"red"} strokeStyle={"solid"} strokeOpacity={1} strokeWeight={3} />
                      </NaverMap>
                    </RenderAfterNavermapsLoaded>
                  </div>
                  <div>
                    <img src={detail.imagePath} alt="인증사진" style={{ width: "65vw", height: "45vh", objectFit: "cover" }} />
                  </div>
                </Slider>
              </div>
            ) : (
              <RenderAfterNavermapsLoaded
                ncpClientId={"cechhl2v8i"} // 자신의 네이버 계정에서 발급받은 Client ID
                error={<p>Maps Load Error</p>}
                loading={<p>Maps Loading...</p>}
              >
                <NaverMap
                  mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
                  style={{
                    width: "65vw", // 네이버지도 가로 길이
                    height: "45vh", // 네이버지도 세로 길이
                    position: "relative",
                    zIndex: 1,
                  }}
                  center={{ lat: centerY, lng: centerX }} // 지도 초기 위치
                  defaultZoom={13} // 지도 초기 확대 배율
                  draggable={false}
                  scrollWheel={false}
                >
                  <Polyline path={detail.coords} strokeColor={"red"} strokeStyle={"solid"} strokeOpacity={1} strokeWeight={3} />
                </NaverMap>
              </RenderAfterNavermapsLoaded>
            )}
          </div>
        </>
      </div>
    </>
  );
}

function mapStateToProps(state) {
  return { userSlice: state.user };
}

export default connect(mapStateToProps)(RecordDetail);
