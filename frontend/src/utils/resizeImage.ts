const dataUriFromFormFile = (file: File) => {
	return new Promise(resolve => {
		const reader = new FileReader();

		reader.onload = () => {
			resolve(reader.result);
		};
		reader.readAsDataURL(file);
	});
};

const resizeImage = (imgEl: HTMLImageElement) => {
	const MAX_SIZE = 400;
	const canvas = document.createElement('canvas');
	let width = imgEl.width;
	let height = imgEl.height;
	if (width > height) {
		if (width > MAX_SIZE) {
			height *= MAX_SIZE / width;
			width = MAX_SIZE;
		}
	} else {
		if (height > MAX_SIZE) {
			width *= MAX_SIZE / height;
			height = MAX_SIZE;
		}
	}
	canvas.width = width;
	canvas.height = height;
	canvas.getContext('2d')?.drawImage(imgEl, 0, 0, width, height);
	return canvas.toDataURL('image/jpeg');
};

const dataURItoBlob = (dataURI: string) => {
	let byteString;
	if (dataURI.split(',')[0].indexOf('base64') >= 0)
		byteString = atob(dataURI.split(',')[1]);
	else byteString = unescape(dataURI.split(',')[1]);
	const mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
	let ia = new Uint8Array(byteString.length);

	for (var i = 0; i < byteString.length; i++) {
		ia[i] = byteString.charCodeAt(i);
	}

	return new Blob([ia], { type: mimeString });
};

const resizeImageToBlob: (file: File) => Promise<Blob> = file => {
	return new Promise(async resolve => {
		const result = await dataUriFromFormFile(file);
		const imgEl = document.createElement('img');
		imgEl.onload = () => {
			if (imgEl.width <= 400 && imgEl.height <= 400) {
				resolve(file);
			} else {
				const resizedDataUri = resizeImage(imgEl);
				resolve(dataURItoBlob(resizedDataUri));
			}
		};
		imgEl.src = result as string;
	});
};

export default resizeImageToBlob;
