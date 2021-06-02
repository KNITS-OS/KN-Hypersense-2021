import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './company-user.reducer';
import { ICompanyUser } from 'app/shared/model/company-user.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICompanyUserProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const CompanyUser = (props: ICompanyUserProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { companyUserList, match, loading } = props;
  return (
    <div>
      <h2 id="company-user-heading" data-cy="CompanyUserHeading">
        Company Users
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Company User
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {companyUserList && companyUserList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Users Uuid</th>
                <th>User Profile</th>
                <th>Business Unit</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {companyUserList.map((companyUser, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${companyUser.id}`} color="link" size="sm">
                      {companyUser.id}
                    </Button>
                  </td>
                  <td>{companyUser.usersUuid}</td>
                  <td>
                    {companyUser.userProfile ? (
                      <Link to={`user-profile/${companyUser.userProfile.id}`}>{companyUser.userProfile.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {companyUser.businessUnit ? (
                      <Link to={`business-unit/${companyUser.businessUnit.id}`}>{companyUser.businessUnit.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${companyUser.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${companyUser.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${companyUser.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Company Users found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ companyUser }: IRootState) => ({
  companyUserList: companyUser.entities,
  loading: companyUser.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CompanyUser);
