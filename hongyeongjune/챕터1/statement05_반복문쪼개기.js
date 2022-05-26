function statement(invoice, plays) {
    let result = `청구 내역 (고객명: ${invoice.customer})\n`;

    // 임시 변수를 함수로 바꾸고 의미에 맞게 이름 바꾸기
    const usd = (aNumber) => {
        return new Intl.NumberFormat("en-US", {
            style: "currency",
            currency: "USD",
            minimumFractionDigits: 2
        }).format(aNumber / 100);
    };

    // 임시 변수를 질의 함수로 바꾸기
    const playFor = (aPerformance) => {
        return plays[aPerformance.playID];
    };

    // (aPerformance, play) -> (aPerformance)
    const amountFor = (aPerformance) => {
        let result = 0;
        // 변수 인라인하기
        switch (playFor(aPerformance).type) {
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
                throw new Error(`알 수 없는 장르:  ${playFor(aPerformance).type}`);
        }
        return result;
    };

    const volumeCreditsFor = (aPerformance) => {
        // 포인트를 적립한다.
        let volumeCredits = 0;
        volumeCredits += Math.max(aPerformance.audience - 30, 0);
        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (playFor(aPerformance).type === "comedy") {
            volumeCredits += Math.floor(aPerformance.audience / 5);
        }
        return volumeCredits;
    };

    // 임시 변수를 함수로 바꾸기
    const totalVolumeCredits = () => {
        let result = 0;
        for (let perf of invoice.performances) {
            result += volumeCreditsFor(perf);
        }
        return result;
    };

    const totalAmount = () => {
      let result = 0;
      for (let perf of invoice.performances) {
          result += amountFor(perf)
      }
      return result;
    }

    for (let perf of invoice.performances) {
        result += ` ${playFor(perf).name}: ${usd(amountFor(perf) / 100)} (${
            perf.audience
        }석)\n`;
    }

    result += `총액: ${usd(totalAmount() / 100)}\n`;
    result += `적립 포인트: ${totalVolumeCredits()} 점\n`;

    return result;
}
