import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import RecommendCard from "./RecommendCard";

const Recommend = ({ title, recommendList }) => {
  return (
    <div>
      {title}
      <Swiper
        slidesPerView={1.6}
        spaceBetween={10}
        centeredSlides={true}
        loop={true}
        className="mySwiper"
      >
        {recommendList.map((mountain) => (
          <SwiperSlide>
            <RecommendCard mountain={mountain} />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default Recommend;
