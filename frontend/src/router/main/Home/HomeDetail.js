import React, { useEffect, useState } from "react";
import Recommend from "./Recommend";
import Send from "../../../config/Send";
import HomeResult from "./HomeResult";

const HomeDetail = () => {
  const [nearMountain, setNearMountain] = useState([]); // 근처 산 리스트
  const [avgMountain, setAvgMountain] = useState([]); // 방문한 평균높이 산 리스트
  const [mountain, setMountain] = useState([]);

  useEffect(() => {
    // 근처 산 리스트 요청
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        Send.get("/mountain/recommend", {
          params: {
            by: "distance",
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          },
        }).then(({ data }) => {
          console.log(data.mountainRecommendResponseDTOList);
          setNearMountain(data.mountainRecommendResponseDTOList);
        });
      });
    } else {
      window.alert("현재 위치를 알 수 없습니다.");
    }

    // 비슷한 높이 산 요청
    Send.get("/mountain/recommend", {
      params: {
        by: "height",
      },
    })
      .then(({ data }) => {
        console.log(data.mountainRecommendResponseDTOList);
        setAvgMountain(data.mountainRecommendResponseDTOList);
      })
      .catch((err) => {});

    // 방문자 순위
    Send.get("/mountain/rank")
      .then(({ data }) => {
        console.log(data.mountainVisitorRankResponseDTOList);
        setMountain(data.mountainVisitorRankResponseDTOList);
      })
      .catch((err) => {});
  }, []);

  return (
    <div>
      <div
        style={{
          height: "40vh",
          marginTop: "3vh",
          marginLeft: "6vw",
          marginRight: "6vw",
        }}
      >
        <div style={{ marginBottom: "4vh" }}>
          <Recommend
            title="숲지기 근처 산"
            recommendList={nearMountain}
            near={true}
          />
        </div>
        <div>
          <Recommend
            title="숲지기가 방문한 산과 유사한 산"
            recommendList={avgMountain}
            near={false}
          />
        </div>
      </div>
      <div
        style={{
          height: "34vh",
          marginTop: "5vh",
          marginLeft: "6vw",
          marginRight: "6vw",
        }}
      >
        <HomeResult mountain={mountain}></HomeResult>
      </div>
    </div>
  );
};

export default HomeDetail;
