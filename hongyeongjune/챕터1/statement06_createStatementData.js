export default function createStatementData(invoice, plays) {
    // 임시 변수를 질의 함수로 바꾸기
    const playFor = (aPerformance) => {
        return plays[aPerformance.playID];
    };

    const amountFor = (aPerformance) => {
        let result = 0;
        // 변수 인라인하기
        switch (aPerformance.play.type) {
            case "tragedy":
                result = 40000;
                if (aPerformance.audience > 30) {
                    result += 1000 * (aPerformance.audience - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (aPerformance.audience > 20) {
                    result += 10000 + 500 * (aPerformance.audience - 20);
                }
                result += 300 * aPerformance.audience;
                break;
            default:
                // 변수 인라인하기
                throw new Error(`알 수 없는 장르:  ${perf.play.type}`);
        }
        return result;
    };

    const volumeCreditsFor = (aPerformance) => {
        // 포인트를 적립한다.
        let volumeCredits = 0;
        volumeCredits += Math.max(aPerformance.audience - 30, 0);
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (aPerformance.play.type === "comedy") {
            volumeCredits += Math.floor(aPerformance.audience / 5);
        }
        return volumeCredits;
    };

    const enrichPerformance = (aPerformance) => {
        const result = Object.assign({}, aPerformance);
        result.play = playFor(result);
        result.amount = amountFor(result);
        result.volumeCredits = volumeCreditsFor(result)
        return result;
    }

    const totalAmount = (data) => {
        // 반복문을 파이프라인으로 변경
        return data.performances
            .reduce((total, p) => total + p.amount, 0);
    }

    // 임시 변수를 함수로 바꾸기
    const totalVolumeCredits = (data) => {
        // 반복문을 파이프라인으로 바꿈
        return data.performances
            .reduce((total, p) => total + p.volumeCredits, 0);
    };

    const statementData = {};
    // 고객 데이터를 중간 데이터로 옮김
    statementData.customer = invoice.customer;
    // 공연 정보를 중간 데이터로 옮김
    statementData.performances = invoice.performances.map(enrichPerformance);

    statementData.totalAmount = totalAmount(statementData);
    statementData.totalVolumeCredits = totalVolumeCredits(statementData);

    return statementData;
}