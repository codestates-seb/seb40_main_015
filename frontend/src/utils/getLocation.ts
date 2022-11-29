const getLocation = function (options: PositionOptions) {
	return new Promise((res, rej) => {
		navigator.geolocation.getCurrentPosition(res, rej, options);
	});
};

export default getLocation;
