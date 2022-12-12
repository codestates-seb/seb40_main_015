import { HiStar } from 'react-icons/hi';

const Star = ({ reviewGrade }: { reviewGrade?: number }) => {
	const grade = [1, 2, 3, 4, 5];
	return (
		<>
			{reviewGrade! && reviewGrade > 0
				? grade.map(item => {
						if (item <= reviewGrade) {
							return <HiStar color="#FF9700" key={item} />;
						} else {
							return <HiStar color="#CCCCCC" key={item} />;
						}
				  })
				: grade.map(item => {
						return <HiStar color="#CCCCCC" key={item} />;
				  })}
		</>
	);
};

export default Star;
