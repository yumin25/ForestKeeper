import React, { useState, useEffect } from "react";
import { RenderAfterNavermapsLoaded, NaverMap, Marker } from "react-naver-maps";

// function NaverMapAPI() {
//   return (
//     <NaverMap
//       mapDivId={"maps-getting-started-uncontrolled"} // default: react-naver-map
//       style={{
//         width: "100vw", // 네이버지도 가로 길이
//         height: "92.5vh", // 네이버지도 세로 길이
//         position: "relative",
//         zIndex: 1,
//       }}
//       defaultCenter={{ lat: 37.554722, lng: 126.970833 }} // 지도 초기 위치
//       defaultZoom={13} // 지도 초기 확대 배율
//     >
//       <Marker
//         position={new window.naver.maps.LatLng({ myLocation })}
//         animation={window.naver.maps.Animation.BOUNCE}
//         onClick={() => {
//           alert("여기는 네이버 입니다.");
//         }}
//       />
//     </NaverMap>
//   );
// }
console.log(window);
function Plogging() {
  const navermaps = window.naver.maps;
  const [myLocation, setMyLocation] = useState("");
  // 현재 위치
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        console.log(position.coords.latitude, position.coords.longitude);
        setMyLocation({
          latitude: position.coords.latitude,
          longitude: position.coords.longitude,
        });
      });
    } else {
      window.alert("현재위치를 알수 없습니다.");
    }
  }, []);

  return (
    <>
      <RenderAfterNavermapsLoaded
        ncpClientId={"vyzp4gjptr"} // 자신의 네이버 계정에서 발급받은 Client ID
        error={<p>Maps Load Error</p>}
        loading={<p>Maps Loading...</p>}
      >
        <NaverMap
          mapDivId={"react-naver-map"} // default: react-naver-map
          style={{
            width: "100vw", // 네이버지도 가로 길이
            height: "92.5vh", // 네이버지도 세로 길이
            position: "relative",
            zIndex: 1,
          }}
          defaultCenter={{ lat: 37.554722, lng: 126.970833 }} // 지도 초기 위치
          defaultZoom={13} // 지도 초기 확대 배율
        >
          <Marker
            position={new navermaps.LatLng(myLocation)}
            animation={navermaps.Animation.BOUNCE}
            onClick={() => {
              alert("여기는 네이버 입니다.");
            }}
          />
        </NaverMap>
      </RenderAfterNavermapsLoaded>
    </>
  );
}
export default Plogging;
