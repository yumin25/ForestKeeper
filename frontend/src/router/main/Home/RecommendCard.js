import React from 'react';

const RecommendCard = ({mountain}) => {
    return (
        <div style={{borderRadius:"15px", background:"lightgreen", padding:"15px"}}>
            <span style={{fontSize:"1.5em"}}> <strong>{mountain.name}</strong> </span>
            <br /> 
            {mountain.address.split(" ")[0]+" "+mountain.address.split(" ")[1]} <br />
            거리: {mountain.value}km
        </div>
    );
};

export default RecommendCard;