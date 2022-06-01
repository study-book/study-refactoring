function createPerformanceCalculator(aPerformance, aPlay) {
    switch (aPlay.type) {
        case "tragedy": return new TragedyCalculator(aPerformance, aPlay);
        case "comedy": return new ComedyCalculator(aPerformance, aPlay);
        default:
            throw new Error(`알 수 없는 장르: ${aPlay.type}`)
    }
}

// 공연료 계산기 클래스
class PerformanceCalculator {
    performance;
    play;

    constructor(aPerformance, aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    get amount() {
        throw new Error('서브클래스에서 처리하도록 설계');
    }

    get volumeCredits() {
        return Math.max(this.performance.audience - 30, 0);
    }
}

class TragedyCalculator extends PerformanceCalculator {
    get amount() {
        let result = 40000;
        if (this.performance.audience > 30) {
            result += 1000 * (this.performance.audience - 30);
        }
        return result;
    }
}

class ComedyCalculator extends PerformanceCalculator {
    get amount() {
        let result = 30000;
        if (this.performance.audience > 20) {
            result += 10000 + 500 * (this.performance.audience - 20);
        }
        result += 300 * this.performance.audience;
        return result;
    }

    get volumeCredits() {
        return super.volumeCredits + Math.floor(this.performance.audience / 5);
    }
}

const createPerformanceCalculator = (aPerformance, aPlay) => {
    switch (aPlay.type) {
        case "tragedy":
            return new TragedyCalculator(aPerformance, aPlay);
        case "comedy":
            return new ComedyCalculator(aPerformance, aPlay);
        default:
            throw new Error(`알 수 없는 장르: ${aPlay.type}`);
    }
}

export default function createStatementData(invoice, plays) {
    // 임시 변수를 질의 함수로 바꾸기
    const playFor = (aPerformance) => {
        return plays[aPerformance.playID];
    };

    const enrichPerformance = (aPerformance) => {
        // 공연료 계산기 생성
        // 공연 정보를 계산기로 전달
        const calculator = createPerformanceCalculator(aPerformance, playFor(aPerformance));
        const result = Object.assign({}, aPerformance);
        result.play = calculator.play;
        result.amount = calculator.amount;
        result.volumeCredits = calculator.volumeCredits;
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