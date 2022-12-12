const SLIDE_IDS = [
	'56e3171d-9620-49a6-9cb1-4f1e3a6b9d91',
	'367941fa-964f-405a-9b25-0074d3c3c0d4',
	'f094b5fb-589c-4c16-a83d-117f4a10343b',
	'799f9d34-3f78-4311-a3f6-c7b9637e3753',
	'd2709aee-aa41-48e3-b9e9-81fd00be45b1',
	'fd8d6902-7420-4aa8-8d0d-771c1dda81df',
];

export const heroSlides = SLIDE_IDS.map(el => {
	return {
		content: <div className="heroimage" />,
		style: {
			backgroundImage: `url("https://velog.velcdn.com/images/2pandi/post/${el}/image.png")`,
		},
	};
});
