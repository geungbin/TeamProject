package k3.contractor.ahs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import k3.contractor.ahs.dto.K3DetailContractor;
import k3.contractor.ahs.mapper.K3ContractorMapper;

@Service
@Transactional
public class K3ContractorService {
	
	
	//생성자 메서드 주입
	private final K3ContractorMapper k3ContractorMapper;
	
	public K3ContractorService(K3ContractorMapper k3ContractorMapper) {
		this.k3ContractorMapper = k3ContractorMapper;
	}
	
	//거래처 삭제 처리
	public void k3DeleteContractor(String contractorCode) {
		k3ContractorMapper.k3DeleteContractor(contractorCode);
	}
	
	//거래처 사업장 코드 중복 확인
	public int k3ContractorCodeCheck(String contractorCode){
		int result = 0;
		result += k3ContractorMapper.k3ContractorCodeCheck(contractorCode);
		return result;
	}
	
	//거래처 사업자 등록번호 중복 확인
	public int k3ContractorBusinessNumCheck(String contractorBusinessNum){
		int result = 0;
		result += k3ContractorMapper.k3ContractorBusinessNumCheck(contractorBusinessNum);
		return result;
	}
	
	//거래처 종사업장 번호 중복확인
	public int k3ContractorBusinessClientNumCheck(String contractorBusinessClientNum){
		int result = 0;
		result += k3ContractorMapper.k3ContractorBusinessClientNumCheck(contractorBusinessClientNum);
		return result;
	}

	//거래처 수정 처리 k3_tb_contractor
	public int k3ModifyDetailContractor(K3DetailContractor k3DetailContractor) {
		int result = k3ContractorMapper.k3ModifyDetailContractor(k3DetailContractor);
		return result;
	}
	//거래처 수정 처리 k3_tb_contractor_staff
	public int k3ModifyContractor(K3DetailContractor k3DetailContractor) {
		int result = k3ContractorMapper.k3ModifyContractor(k3DetailContractor);
		return result;
	}
	
	//거래처 수정으로 화면전환
	public K3DetailContractor k3GetModifyContractor(String contractorCode) {
		K3DetailContractor k3DetailContractor = k3ContractorMapper.k3GetModifyContractor(contractorCode);
		
		return k3DetailContractor;
	}
	
	//등록페이지에 거래처 아이디 정해주기
	public List<Map<String, Object>> getContractorIdPw() {
		
		List<Map<String, Object>> contractorIdPw = k3ContractorMapper.getContractorIdPw();

		System.out.println("서비스 변수 내부 확인 contractorIdPw : " + contractorIdPw);

		
		return contractorIdPw;
	}

	//거래처 등록 k3_tb_contractor_staff
	public int k3AddContractor(K3DetailContractor k3ContractorDetail) {
		int result = k3ContractorMapper.k3AddContractor(k3ContractorDetail);
		
		return result;
	}
	//거래처 등록 k3_tb_contractor
	public int k3AddDetailContractor(K3DetailContractor k3DetailContractor) {
		int result = k3ContractorMapper.k3AddDetailContractor(k3DetailContractor);
				
		return result;
	}
	
	
	
	//거래처 조회(검색)
	public Map<String, Object> K3SearchContractorList(String searchKey, String contractorRegistrationDateStart, String contractorRegistrationDateEnd, String searchValue, int currentPage)
	{ 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> contractorListCount = k3ContractorMapper.K3SearchContractorCount(paramMap, searchKey, contractorRegistrationDateStart, contractorRegistrationDateEnd, searchValue);
		
		
		currentPage = 1;
		
		// 검색된 거래처 투플 수(페이징)
		double rowCount = contractorListCount.size();
		
		// 보여줄 행의 개수
		int rowPerPage = 1;
		if(rowCount < 4) {
			rowPerPage = (int) rowCount;
		}else {
			rowPerPage = 4;
		}
		System.out.println("몇개일까" + rowCount + "얼마나 보여줄까" + rowPerPage);
		
		//마지막 페이지
		int lastPage = (int) Math.ceil((rowCount / rowPerPage));
		if(rowPerPage == 0) {
			lastPage = 1;
		}
		
		//페이지 알고리즘
		int startNum = (currentPage - 1) * rowPerPage;
		
		// 보여줄 시작 페이지 번호
		int startPageNum = 1;
		
		// 보여줄 마지막 페이지 번호
		int endPageNum = 0;
		if(lastPage > 5) {
			endPageNum = 5;
		}else {
			endPageNum = lastPage;
		}
		
		// 동적 페이지 구성 (7페이지 부터)
		if(currentPage > 5) {
			startPageNum = currentPage - 2;
			endPageNum = currentPage + 2;
			if(endPageNum > lastPage) {
				startPageNum = lastPage - 4;
				endPageNum = lastPage;
			}
		}

		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String,Object>> contractorList = k3ContractorMapper.K3SearchContractorList(paramMap, searchKey, contractorRegistrationDateStart, contractorRegistrationDateEnd, searchValue);

		paramMap.clear();
		
		paramMap.put("lastPage", lastPage);
		paramMap.put("contractorList", contractorList);
		paramMap.put("startPageNum", startPageNum);
		paramMap.put("endPageNum", endPageNum);
		
		return paramMap;
		
	}
	
	
	//거래처 리스트(상세) 모달창
	public K3DetailContractor K3GetDetailContractor(String contractorCode){
		K3DetailContractor k3DetailContractor = k3ContractorMapper.K3GetDetailContractor(contractorCode); 
		
			
		return k3DetailContractor;
	}
	
	//거래처 리스트(간단) 현황에 불러오기(페이징)
	public Map<String, Object> K3GetContractorList(int currentPage){
		
		//거래처 정보 테이블 행의 개수
		double rowCount = k3ContractorMapper.K3GetContractorCount();
		
		// 보여줄 행의 개수
		int rowPerPage = 0;
		if(rowCount < 4) {
			rowPerPage = (int) rowCount;
		}else {
			rowPerPage = 4;
		}
		
		
		//마지막 페이지
		int lastPage = (int) Math.ceil((rowCount / rowPerPage));
		if(rowPerPage == 0) {
			lastPage = 1;
		}
		
		//페이지 알고리즘
		int startNum = (currentPage - 1) * rowPerPage;
		
		// 보여줄 시작 페이지 번호
		int startPageNum = 1;
		
		// 보여줄 마지막 페이지 번호
		int endPageNum = 0;
		if(lastPage > 5) {
			endPageNum = 5;
		}else {
			endPageNum = lastPage;
		}
		
		System.out.println("보여줄 페이지 마지막번호 : "+ endPageNum+ "-->끝");
		
		// 동적 페이지 구성 (7페이지 부터)
		if(currentPage > 5) {
			startPageNum = currentPage - 2;
			endPageNum = currentPage + 2;
			if(endPageNum > lastPage) {
				startPageNum = lastPage - 4;
				endPageNum = lastPage;
			}
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String,Object>> contractorList = k3ContractorMapper.K3GetContractorList(paramMap);
		
		paramMap.clear();
		
		paramMap.put("lastPage", lastPage);
		paramMap.put("contractorList", contractorList);
		paramMap.put("startPageNum", startPageNum);
		paramMap.put("endPageNum", endPageNum);
		
		return paramMap;
	}



}

