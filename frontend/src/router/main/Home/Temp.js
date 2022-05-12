import React, { useEffect, useState } from "react";
import Recommend from "./Recommend";
import Send from "../../../config/Send";

const Temp = () => {
  const [nearMountain, setNearMountain] = useState([]); // 근처 산 리스트
  const [avgMountain, setAvgMountain] = useState([]); // 방문한 평균높이 산 리스트
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
          console.log(data.recommendResponseDTOList);
          setNearMountain(data.recommendResponseDTOList);
        });
      });
    } else {
      window.alert("현재위치를 알수 없습니다.");
    }

    // 비슷한 높이 산 요청
    Send.get("/mountain/recommend", {
      params: {
        by: "height",
      },
    })
      .then(({ data }) => {
        console.log(data.recommendResponseDTOList);
        setAvgMountain(data.recommendResponseDTOList);
      })
      .catch((err) => {});
  }, []);
  return (
    <div>
      <Recommend title="근처 산" recommendList={nearMountain} near={true} />
      <Recommend
        title="방문했던 평균 높이 산"
        recommendList={avgMountain}
        near={false}
      />
    </div>
  );
};

export default Temp;
