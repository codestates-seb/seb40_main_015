const convertDate = (from: string, to: string, day: boolean = false) => {
	const week = ['일', '월', '화', '수', '목', '금', '토'];
	const fromDate = new Date(from);
	const toDate = new Date(to);
	let result = '';
	if (day) {
		result = `${fromDate.getFullYear()}.${
			fromDate.getMonth() + 1
		}.${fromDate.getDate()} ${
			week[fromDate.getDay()]
		} ~ ${toDate.getFullYear()}.${toDate.getMonth() + 1}.${toDate.getDate()} ${
			week[toDate.getDay()]
		}`;
	} else {
		result = `${fromDate.getFullYear()}. ${
			fromDate.getMonth() + 1
		}. ${fromDate.getDate()} ~ ${toDate.getFullYear()}. ${
			toDate.getMonth() + 1
		}. ${toDate.getDate()}`;
	}

	return result;
};

export default convertDate;
