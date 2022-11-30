import { createSlice } from '@reduxjs/toolkit';

interface InitProps {
  [key: string]: any;
  memberId: number;
  nickname: string;
  location: {
    latitude:string;
    longitude:string;
  };
  address: string;
  totalBookCount: number;
	avatarUrl:string;
  avgGrade: number;
}

const initialState: InitProps = {
  memberId: 0,
  nickname: '',
  location: {
    latitude:"",
    longitude:""
  },
  address: '',
  totalBookCount: 0,
	avatarUrl:"",
  avgGrade: 0
}

const userInfoSlice = createSlice({
  name: 'userInfo',
  initialState,
  reducers: {
    setUserInfo: (_, action) => action.payload,
    updateUserInfo: (state, action) => {
      const {key} = action.payload;
      state[key] = action.payload.value;
    }
  }
})

export const {setUserInfo, updateUserInfo} = userInfoSlice.actions;
export default userInfoSlice.reducer;