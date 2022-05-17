import { createSlice } from "@reduxjs/toolkit";

const initialUserState = {
  userEmail: "",
  userNickname: "",
  userName: "",
  userImage: "",
};

const userSlice = createSlice({
  name: "user",
  initialState: initialUserState,
  reducers: {
    save: (state, action) => {
      state.userEmail = action.payload.email;
      state.userNickname = action.payload.nickname;
      state.userName = action.payload.name;
      state.userImage = action.payload.thumbnailPath;
    },
    update: (state, action) => {
      state.userNickname = action.payload.nickname;
      state.userImage = action.payload.image;
    },
  },
});

export const { save, update } = userSlice.actions;
export default userSlice.reducer;
