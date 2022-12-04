import { useState, useEffect } from 'react';

interface locationType {
  loaded: boolean;
  coordinates?: { latitude: number; longitude: number };
  error?: { code: number; message: string };
}

const useGeolocation2 = () => {
  const [location, setLocation] = useState<locationType>({
    loaded: false,
    coordinates: { latitude: 0, longitude: 0, }
  })

  // 성공에 대한 로직
  const onSuccess = (location: { coords: { latitude: number; longitude: number; }; }) => {
    setLocation({
      loaded: true,
      coordinates: {
        latitude: location.coords.latitude,
        longitude: location.coords.longitude,
      }
    })
  }

  // 에러에 대한 로직
  const onError = (error: { code: number; message: string; }) => {
    setLocation({
      loaded: true,
      error,
    })
  }

  useEffect(() => {
    // navigator 객체 안에 geolocation이 없다면
    // 위치 정보가 없는 것.
    if (!("geolocation" in navigator)) {
      onError({
        code: 0,
        message: "Geolocation not supported",
      })
    }
    navigator.geolocation.getCurrentPosition(onSuccess, onError);
  }, [])

  return location;
}

export default useGeolocation2