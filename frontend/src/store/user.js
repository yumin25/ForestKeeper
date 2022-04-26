import { createSlice } from "@reduxjs/toolkit";

const initialUserState = {
  userId: "",
  userEmail: "",
  userNickname: "",
  userName: "",
};

const userSlice = createSlice({
  name: "user",
  initialState: initialUserState,
  reducers: {
    save: (state, action) => {
      state.userId = action.payload.id;
      state.userEmail = action.payload.user_email;
      state.userNickname = action.payload.user_nickname;
      state.userName = action.payload.user_name;
    },
    update: (state, action) => {
      state.userNickname = action.payload.nickname;
    },
  },
});

export const { save, update } = userSlice.actions;
export default userSlice.reducer;
